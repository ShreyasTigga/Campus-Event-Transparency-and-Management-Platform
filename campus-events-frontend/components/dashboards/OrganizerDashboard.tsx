export default function OrganizerDashboard() {
  return (
    <div className="p-8">
      <h1 className="text-4xl font-bold mb-6">📋 Organizer Dashboard</h1>

      <div className="grid md:grid-cols-3 gap-6">
        <Card
          title="Create Event"
          desc="Submit a new event for admin approval"
        />
        <Card
          title="My Events"
          desc="Track approval status of your events"
        />
        <Card
          title="Participants"
          desc="View students enrolled in your events"
        />
      </div>
    </div>
  );
}

function Card({ title, desc }: { title: string; desc: string }) {
  return (
    <div className="bg-white/10 backdrop-blur-lg p-6 rounded-xl border border-white/10 hover:border-purple-500 transition">
      <h2 className="text-xl font-semibold mb-2">{title}</h2>
      <p className="text-white/70">{desc}</p>
    </div>
  );
}
